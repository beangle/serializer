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

package org.beangle.serializer.fst

import org.beangle.commons.logging.Logging
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import org.beangle.commons.io.DefaultBinarySerializer
import java.io.ObjectInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

class FSTSerializerTest extends AnyFunSpec with Matchers with Logging {

  val serializer = new FSTSerializer

  val account = new Account("0001", "root")
  account.remoteToken = Some("OTHER_token")
  account.authorities = "12,3,4"
  account.details = Map("category" -> "1")

  describe("FSTSerializer") {
    it("serialize") {
      serializer.registerClass(classOf[Account])
      val data = serializer.asBytes(account)
      println("Account data has " + data.length + " bytes using fst serializer.")
      val newAccount = serializer.asObject(classOf[Account], data)
      assert(newAccount.remoteToken == Some("OTHER_token"))
    }
  }

  describe("DefaultBinarySerializer") {
    it("serialize") {
      DefaultBinarySerializer.registerClass(classOf[Account])
      val data = DefaultBinarySerializer.asBytes(account)
      println("Account data has " + data.length + " bytes using default serializer.")
      val newAccount = DefaultBinarySerializer.asObject(classOf[Account], data)
      assert(newAccount.authorities == account.authorities)
      assert(newAccount.authorities.contains("12"))
    }
  }

}
