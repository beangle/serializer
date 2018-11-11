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
package org.beangle.serializer.json

import java.io.File
import org.junit.runner.RunWith
import org.scalatest.{ FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class JsonSerializerTest extends FunSpec with Matchers {

  describe("JsonSerializer") {
    it("serializer json ") {
      val json = JsonSerializer()
      json.alias("person", classOf[Person])
      json.alias("address", classOf[Address])
      json.alias("list", classOf[::[_]])

      //println(json.serialize(List(Some(new Person("002", "admin2")), new Person("001", "admin"))))
      //println(json.serialize(Array("a", "b")))
      //println(json.serialize(new Member))
    }
    it("serializer jsonp ") {
      val jsonp = JsonpSerializer()
      jsonp.alias("person", classOf[Person])
      jsonp.alias("address", classOf[Address])
      jsonp.alias("list", classOf[::[_]])
      //println(jsonp.serialize(List(Some(new Person("002", "admin2")), new Person("001", "admin"))))
      //println(jsonp.serialize(Array("a", "b")))
      //println(jsonp.serialize(new Member))
    }
  }
}
