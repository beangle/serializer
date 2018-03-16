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
package org.beangle.serializer.text

import org.beangle.serializer.text.io.StreamWriter
import org.beangle.serializer.text.marshal.MarshallingContext
import org.beangle.serializer.text.marshal.Marshaller
import org.beangle.commons.io.Serializer

trait StreamSerializer extends Serializer {

  def serialize(obj: Any, writer: StreamWriter, params: Map[String, Any])

  def marshalNull(obj: Any, property: String, context: MarshallingContext)

  def marshal(item: Any, marshaller: Marshaller[Any], context: MarshallingContext): Unit

  def hierarchical: Boolean
}
