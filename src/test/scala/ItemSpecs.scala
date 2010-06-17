import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import net.liftweb.common.{Box, Empty, Full, Failure}
import net.liftweb.mapper._
import java.sql.{Connection, DriverManager}

class ItemSpecs extends FlatSpec with ShouldMatchers {
	
	DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
	Schemifier.schemify(true, Schemifier.infoF _, Item)
	
	"Item" should "be savable after a field has been changed" in {
		val item = Item.create.tmdbId(1L).saveMe
		item.name("test").save
	}
}

object DBVendor extends ConnectionManager {
 def newConnection(name: ConnectionIdentifier): Box[Connection] = {
   try {
     Class.forName("com.mysql.jdbc.Driver")
     val dm = DriverManager.getConnection("jdbc:mysql://localhost/bug_report?user=root&password=")
     Full(dm)
   } catch {
     case e : Exception => e.printStackTrace; Empty
   }
 }
 def releaseConnection(conn: Connection) {conn.close}
}