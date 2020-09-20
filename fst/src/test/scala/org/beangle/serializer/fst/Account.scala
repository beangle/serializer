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
package org.beangle.serializer.fst

import java.io.{ Externalizable, ObjectInput, ObjectOutput }
import java.security.Principal

import org.beangle.commons.collection.Collections

final class Account extends Externalizable with Serializable {

  var name: String = _

  var description: String = _

  var remoteToken: Option[String] = None

  var status: Int = _

  var authorities: String = _

  var permissions: String = _

  var details: Map[String, String] = Map.empty

  def this(name: String, description: String) = {
    this()
    this.name = name
    this.description = description
  }

  private def change(value: Boolean, mask: Int): Unit = {
    if (value) status = status | mask
    else {
      if ((status & mask) > 0) status = status ^ mask
    }
  }

  def writeExternal(out: ObjectOutput): Unit = {
    println("write external")
    out.writeObject(name)
    out.writeObject(description)
    out.writeObject(remoteToken.orNull)
    out.writeInt(status)
    out.writeObject(authorities)
    out.writeObject(permissions)
    out.writeInt(details.size)
    details foreach {
      case (k, v) =>
        out.writeObject(k)
        out.writeObject(v)
    }
  }

  def readExternal(in: ObjectInput): Unit = {
    println("read external")
    name = in.readObject.toString
    description = in.readObject.toString
    remoteToken = Option(in.readObject.asInstanceOf[String])
    status = in.readInt()
    authorities = in.readObject.asInstanceOf[String]
    permissions = in.readObject.asInstanceOf[String]
    val mapSize = in.readInt()
    val temp = Collections.newMap[String, String]
    (0 until mapSize) foreach { i =>
      val k = in.readObject.toString
      val v = in.readObject.toString
      temp += (k -> v)
    }
    details = temp.toMap
  }
}
