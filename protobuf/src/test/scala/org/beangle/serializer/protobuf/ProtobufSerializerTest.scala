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

      val os = new ByteArrayOutputStream
      serializer.serialize(account, os, Map.empty)
      val data = os.toByteArray()
      println("Account data has " + data.length + " bytes using protobuf serializer.")
      val is = new ByteArrayInputStream(data)
      val newAccount = serializer.deserialize(classOf[Account], is, Map.empty)
      assert(newAccount.remoteToken == Some("OTHER_token"))
    }
  }
}
