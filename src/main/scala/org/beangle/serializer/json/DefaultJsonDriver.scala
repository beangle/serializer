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

import org.beangle.commons.io.BufferedWriter
import org.beangle.serializer.text.io.{AbstractDriver, StreamDriver, StreamWriter}

import java.io.Writer

class DefaultJsonDriver(encoding: String = "UTF-8") extends AbstractDriver(encoding), JsonDriver {

  def createWriter(out: Writer, params: Map[String, Any]): StreamWriter = {
    if (params.contains("pretty")) new PrettyJsonWriter(new BufferedWriter(out), registry)
    else new DefaultJsonWriter(new BufferedWriter(out), registry)
  }
}

class DefaultJsonpDriver(encoding: String = "UTF-8") extends AbstractDriver(encoding), StreamDriver {

  def createWriter(out: Writer, params: Map[String, Any]): StreamWriter = {
    new DefaultJsonpWriter(new BufferedWriter(out), registry)
  }
}
