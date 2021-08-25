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

package org.beangle.serializer.protobuf

import org.beangle.commons.io.ObjectSerializer
import java.io.InputStream
import java.io.OutputStream

class AccountProtobufSerializer extends ObjectSerializer {

  def serialize(data: Any, os: OutputStream, params: Map[String, Any]): Unit = {
    val account = data.asInstanceOf[Account]
    val builder = AccountProtobuf.Account.newBuilder()
    builder.setName(account.name)
    builder.setDescription(account.description)
    builder.setStatus(account.status)
    account.remoteToken foreach { t =>
      builder.setRemoteToken(t)
    }
    if (null != account.authorities) builder.setAuthorities(account.authorities)
    if (null != account.permissions) builder.setPermissions(account.permissions)
    account.details foreach {
      case (k, v) =>
        builder.putDetails(k, v)
    }

    builder.build().writeTo(os)
  }

  def deserialize(is: InputStream, params: Map[String, Any]): Any = {
    val pa = AccountProtobuf.Account.parseFrom(is)
    val account = new Account(pa.getName, pa.getDescription)
    account.status = pa.getStatus
    account.authorities = pa.getAuthorities
    account.permissions = pa.getPermissions
    account.remoteToken = Option(pa.getRemoteToken)
    val dk = pa.getDetailsMap.entrySet().iterator()
    while (dk.hasNext) {
      val entry = dk.next()
      account.details += (entry.getKey -> entry.getValue)
    }
    account
  }
}
