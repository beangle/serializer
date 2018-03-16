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
package org.beangle.serializer.text.marshal

import java.{ util => ju }
import org.junit.runner.RunWith
import org.scalatest.{ Finders, FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner
import org.beangle.commons.collection.page.SinglePage
import org.beangle.serializer.text.mapper.DefaultMapper
import org.beangle.serializer.text.Skill

@RunWith(classOf[JUnitRunner])
class DefaultMarshallerRegistryTest extends FunSpec with Matchers {

  val registry = new DefaultMarshallerRegistry(new DefaultMapper)

  describe("DefaultMarshallerRegistry") {
    it("lookup marshaller ") {
      val skills = Array(new Skill("Play Basketball Best"), new Skill("Play football"))
      val converter = registry.lookup(skills.getClass)
      val converter2 = registry.lookup(skills.getClass)
      assert(converter != null)
      assert(converter.support(skills.getClass))
      assert(converter.getClass == classOf[ArrayMarshaller])

      val dateMarshaller = registry.lookup(classOf[ju.Date])
      assert(dateMarshaller != null)
      assert(dateMarshaller.isInstanceOf[DateMarshaller])
    }
    it("lookup page marshaller") {
      val marshaller = registry.lookup(classOf[SinglePage[_]])
      assert(null != marshaller)
      assert(marshaller.targetType == Type.Object)
    }
  }
}
