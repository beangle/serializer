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
package org.beangle.serializer.text.io

import org.junit.runner.RunWith
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PathStackTest extends AnyFunSpec with Matchers {

  describe("PathStack") {
    it("push and pop") {
      val stack = new PathStack(16)
      stack.push("table", null)
      stack.push("tr", null)
      stack.push("td", null)
      stack.push("form", null)
      stack.pop()
      stack.pop()
      stack.push("td", null)
      stack.push("div", null)
      assert("table[1]/tr[1]/td[2]/div[1]" == stack.currentPath.explicit)
    }
  }
}
