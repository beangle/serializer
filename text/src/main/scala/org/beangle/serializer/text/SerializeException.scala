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

package org.beangle.serializer.text

import java.{util => ju}

object SerializeException {
  final val SEPARATOR = "\n-------------------------------"
}

class SerializeException(message: String, cause: Throwable = null) extends RuntimeException(message, cause) {
  private val stuff = new ju.TreeMap[String, String]()

  def add(name: String, information: String): Unit = {
    var key = name
    var i = 0
    while (stuff.containsKey(key)) {
      val value = stuff.get(key)
      if (information.equals(value))
        return
      i = i + 1
      key = name + "[" + i + "]"
    }
    stuff.put(key, information)
  }

}
