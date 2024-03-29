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

package org.beangle.serializer.json

import java.io.Writer

import org.beangle.serializer.text.marshal.{MarshallerRegistry, MarshallingContext}

class DefaultJsonpWriter(writer: Writer, registry: MarshallerRegistry) extends DefaultJsonWriter(writer, registry) {
  var callbackName: String = "callback"

  override def start(context: MarshallingContext): Unit = {
    val callback = context.params.getOrElse(callbackName, "callback").asInstanceOf[String]
    writer.write(callback)
    writer.write('(')
  }

  override def end(context: MarshallingContext): Unit = {
    writer.write(')')
  }
}
