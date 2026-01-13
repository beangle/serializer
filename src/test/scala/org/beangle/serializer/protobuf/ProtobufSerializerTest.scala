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

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class ProtobufSerializerTest extends AnyFunSpec, Matchers {
  describe("ProtobufSerializer") {
    it("serializing") {
      val account = new Account("0001", "root")
      account.remoteToken = Some("OTHER_token")
      account.authorities = "12,3,4"
      account.details = Map("category" -> "1")

      val serializer = new ProtobufSerializer()
      serializer.registerClass(classOf[Account])

      val data = serializer.asBytes(account)
      println("Account data has " + data.length + " bytes using protobuf serializer.")
      val newAccount = serializer.asObject(classOf[Account], data)
      assert(newAccount.remoteToken.contains("OTHER_token"))
    }
  }
}
