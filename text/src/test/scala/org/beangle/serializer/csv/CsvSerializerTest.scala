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

import org.junit.runner.RunWith
import org.scalatest.Matchers
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CsvSerializerTest extends AnyFunSpec with Matchers {

  describe("CsvSerializer") {
    it("serializer csv ") {
      val csv = CsvSerializer()
      val params = Map("properties" -> List(
        classOf[Person] -> List("code", "address", "sidekick", "skills"),
        classOf[Address] -> List("street", "name")))
      //println(csv.serialize(List(new Person("002", "admin2"), new Person("001", "admin")), params))
    }
    it("serializer page ignore collection") {
      val csv = CsvSerializer()
      val params = Map("properties" -> List(
        classOf[Person] -> List("code", "address", "sidekick", "skills"),
        classOf[Address] -> List("street", "name")))
      //println(csv.serialize(new SinglePage(1, 2, 100, List(new Person("002", "admin2"), new Person("001", "admin"))), params))
    }
  }
}
