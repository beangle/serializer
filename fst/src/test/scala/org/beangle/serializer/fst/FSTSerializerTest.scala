package org.beangle.serializer.fst

import org.beangle.commons.logging.Logging
import org.junit.runner.RunWith
import org.scalatest.{ FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner
import org.beangle.commons.io.DefaultBinarySerializer
import java.io.ObjectInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

@RunWith(classOf[JUnitRunner])
class FSTSerializerTest extends FunSpec with Matchers with Logging {

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