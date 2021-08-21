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

package org.beangle.serializer.text.marshal

import org.beangle.serializer.text.io.StreamWriter
import org.beangle.serializer.text.marshal.Type.Type

/**
  * 具体某一类型的序列化
  */
trait Marshaller[T] {
  def marshal(source: T, writer: StreamWriter, context: MarshallingContext): Unit

  def support(clazz: Class[_]): Boolean = {
    true
  }

  def targetType: Type = {
    Type.String
  }

  def extractOption(item: Any): Any = {
    if (item == null) {
      null
    } else {
      item match {
        case o: Option[Any] => o.orNull.asInstanceOf[AnyRef]
        case _ => item
      }
    }
  }
}
