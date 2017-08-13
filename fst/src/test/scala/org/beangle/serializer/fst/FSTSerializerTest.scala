package org.beangle.serializer.fst

import org.beangle.commons.logging.Logging
import org.junit.runner.RunWith
import org.scalatest.{ FunSpec, Matchers }
import org.scalatest.junit.JUnitRunner
import org.beangle.commons.io.DefaultBinarySerializer
import java.io.ObjectInputStream
import java.io.ByteArrayInputStream

@RunWith(classOf[JUnitRunner])
class FSTSerializerTest extends FunSpec with Matchers with Logging {

  val serializer = new FSTSerializer

  val account = new Account("0001", "root")
  account.remoteToken = None
  account.authorities = Set(12L, 3L, 4L)
  account.details = Map("category" -> "1")

  describe("FSTSerializer") {
    it("serialize") {
      serializer.register(classOf[Account])
      val data = serializer.serialize(account, Map.empty)
      val newAccount = serializer.deserialize(data, Map.empty).asInstanceOf[Account]
      assert(newAccount.remoteToken == None)
    }
  }

  describe("DefaultBinarySerializer") {
    it("serialize") {
      val data = DefaultBinarySerializer.serialize(account, Map.empty)
      val newAccount = DefaultBinarySerializer.deserialize(data, Map.empty).asInstanceOf[Account]
      assert(newAccount.authorities == account.authorities)
      assert(newAccount.authorities.contains(12L))
    }
  }

}