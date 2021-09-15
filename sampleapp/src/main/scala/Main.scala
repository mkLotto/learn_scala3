import scalikejdbc.interpolation.Implicits._
import scalikejdbc.{NamedDB, ConnectionPool, _}
import play.api.libs.json._
import play.api.libs.functional.syntax._

@main def main: Unit =
  // TODO: scalikejdbc.config.DBs.setupAll() does not work
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.add(Symbol("demo"), "jdbc:mysql://localhost:3306/demo", "root", "")
  NamedDB(Symbol("demo")) localTx { implicit session =>
    prepareEnv()
    val fetchedMember = MemberDAO.selectBy("Alice")
    println(Json.toJson(fetchedMember))
    val fetchedMembers = MemberDAO.selectAll()
    println(Json.toJson(MemberListRespoonse(fetchedMembers)))
  }
  deleteEnv()

def prepareEnv()(using session: DBSession): Unit =
  MemberDAO.createTable()
  List("Alice", "David", "Taro").foreach(MemberDAO.createBy)

def deleteEnv(): Unit =
  NamedDB(Symbol("demo")) localTx { implicit _ => MemberDAO.dropTable() }

case class MemberListRespoonse(data: List[MemberDTO])

object MemberListRespoonse {
  implicit val writes: Writes[MemberListRespoonse] = Json.writes[MemberListRespoonse]
}

case class MemberDTO(id: Long, name: String, createdAt: String)

object MemberDTO extends SQLSyntaxSupport[MemberDTO]  {
  override val connectionPoolName = Symbol("demo")
  override val tableName = "members"

  val * : WrappedResultSet => MemberDTO = set =>
    MemberDTO(
      id = set.long("id"),
      name = set.string("name"),
      createdAt = set.string("created_at")
    )

  implicit val writes: Writes[MemberDTO] = Json.writes[MemberDTO]
}

object MemberDAO {
  import MemberDTO.*

  def selectAll()(implicit session: DBSession): List[MemberDTO] =
    sql"select * from members"
      .map(MemberDTO.*).list.apply()

  def selectBy(name: String)(implicit session: DBSession): Option[MemberDTO] =
    sql"""
         select * from members
         where
           name = ${name}
         limit 1
   """.map(MemberDTO.*).single.apply()

  def createTable()(implicit session: DBSession): Unit =
    sql"""
          create table members (
            id serial not null primary key,
            name varchar(64),
            created_at timestamp not null
    )""".execute.apply()

  def createBy(name: String)(implicit session: DBSession): Unit =
    sql"""
         insert into members (
            name,
            created_at)
         values (
            ${name},
            current_timestamp)
         """.update.apply()

  def dropTable()(implicit session: DBSession): Unit =
      sql"""drop table members""".execute.apply()
}


