/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.serializer.csv

import java.io.FileOutputStream
import java.util.Date

import org.beangle.serializer.text.AbstractSerializer
import org.beangle.serializer.text.marshal.MarshallingContext
import org.junit.runner.RunWith
import org.scalatest.{ FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DefaultCsvWriterTest extends FunSpec with Matchers {

  describe("DefaultCsvWriter") {
    it("getAttributes") {
      val serializer = CsvSerializer().asInstanceOf[AbstractSerializer]
      val os = new FileOutputStream("/tmp/a.csv")
      val params = Map("properties" -> List(
        classOf[Person] -> List("code", "name", "accountMoney1", "bestSkill", "skills", "families", "sidekick", "address"),
        classOf[Skill] -> List("name")))
      val writer = serializer.driver.createWriter(os, params).asInstanceOf[DefaultCsvWriter]
      val context = new MarshallingContext(serializer, writer, serializer.registry, params)

      val personProperties = context.getProperties(classOf[Person])
      assert(personProperties != null)
      assert(personProperties.size == 6)
      assert(context.getProperties(classOf[java.util.Date]) == List())
      assert(context.getProperties(classOf[Address]) != null)
      assert(context.getProperties(classOf[Address]).size == 3)

      var csvProperties = writer.getProperties(context)
      assert(csvProperties.length == 8)
    }
  }
}
