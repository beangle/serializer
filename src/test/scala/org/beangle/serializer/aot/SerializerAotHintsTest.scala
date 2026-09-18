/*
 * Copyright (C) 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.beangle.serializer.aot

import org.beangle.commons.aot.AotHintGenerator
import org.beangle.commons.json.{Json, JsonArray, JsonObject}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import java.nio.charset.StandardCharsets
import java.nio.file.Files

class SerializerAotHintsTest extends AnyFunSpec, Matchers {

  private def reflectEntries(hints: org.beangle.commons.aot.AotHints): Vector[JsonObject] = {
    val dir = Files.createTempDirectory("serializer-aot-hints")
    AotHintGenerator.writeReachabilityMetadata(dir, hints)
    val json = Files.readString(dir.resolve("reachability-metadata.json"), StandardCharsets.UTF_8)
    val reflection = Json.parseObject(json).get("reflection").get.asInstanceOf[JsonArray]
    reflection.toVector.map(_.asInstanceOf[JsonObject])
  }

  private def ctorNames(entry: JsonObject): Vector[(String, Boolean)] =
    entry.get("methods") match {
      case None => Vector.empty
      case Some(methods) =>
        methods.asInstanceOf[JsonArray].toVector.map { m =>
          val method = m.asInstanceOf[JsonObject]
          (method.get("name").get.toString, method.get("parameterTypes").get.asInstanceOf[JsonArray].isEmpty)
        }
    }

  describe("SerializerAotHints") {
    it("registers the no-arg constructor of java.lang.Object") {
      val registrar = new SerializerAotHints
      registrar.registering()
      registrar.aotHints.getConstructors should contain("java.lang.Object")
    }

    it("emits java.lang.Object <init> so ObjectStreamClass can instantiate serializable types") {
      val registrar = new SerializerAotHints
      registrar.registering()
      val entries = reflectEntries(registrar.aotHints)
      val obj = entries.find(e => e.get("type").get.toString == "java.lang.Object")
      obj should not be empty
      ctorNames(obj.get) should contain(("<init>", true))
    }

    it("keeps JDK serializable types registered") {
      val registrar = new SerializerAotHints
      registrar.registering()
      val serializables = reflectEntries(registrar.aotHints)
        .filter(e => e.get("serializable").contains(true))
        .map(e => e.get("type").get.toString)
      serializables should contain allOf ("java.lang.String", "java.util.Date")
    }
  }
}
