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
import org.beangle.serializer.text.mapper.Mapper

abstract class AbstractIterableMarshaller[T <: Iterable[_]](val mapper: Mapper) extends Marshaller[T] {

  protected def writeItem(item: Object, writer: StreamWriter, context: MarshallingContext): Unit = {
    val realitem = extractOption(item)
    if (realitem == null) {
      writer.startNode(mapper.serializedClass(classOf[Null]), classOf[Null])
    } else {
      val name = mapper.serializedClass(realitem.getClass)
      writer.startNode(name, realitem.getClass)
      context.marshal(realitem)
    }
    writer.endNode()
  }

  override def targetType: Type = {
    Type.Collection
  }
}

abstract class AbstractCollectionMarshaller[T](val mapper: Mapper) extends Marshaller[T] {

  protected def writeItem(item: Object, writer: StreamWriter, context: MarshallingContext): Unit = {
    val realitem = extractOption(item)
    if (realitem == null) {
      writer.startNode(mapper.serializedClass(classOf[Null]), classOf[Null])
    } else {
      val name = mapper.serializedClass(realitem.getClass)
      writer.startNode(name, realitem.getClass)
      context.marshal(realitem)
    }
    writer.endNode()
  }

  override def targetType: Type = {
    Type.Collection
  }
}
