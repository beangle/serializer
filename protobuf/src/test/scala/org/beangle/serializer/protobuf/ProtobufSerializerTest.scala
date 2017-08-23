package org.beangle.serializer.protobuf

import java.io.{ ByteArrayInputStream, ByteArrayOutputStream }

import org.beangle.commons.logging.Logging
import org.junit.runner.RunWith
import org.scalatest.{ FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ProtobufSerializerTest extends FunSpec with Matchers with Logging {
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
      assert(newAccount.remoteToken == Some("OTHER_token"))
    }
  }
}
