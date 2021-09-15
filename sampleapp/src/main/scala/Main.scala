import scalikejdbc.interpolation.Implicits._
import scalikejdbc.{NamedDB, ConnectionPool, _}
import play.api.libs.json._
import play.api.libs.functional.syntax._

object sampleApp:
  def main(args: Array[String]): Unit =
    // TODO: scalikejdbc.config.DBs.setupAll() does not work
    Class.forName("com.mysql.jdbc.Driver")
    ConnectionPool.add(Symbol("demo"), "jdbc:mysql://localhost:3306/demo", "root", "")
    println("What do you want to do?(create table, register, search, update, drop table")
    NamedDB(Symbol("demo")) localTx { implicit session =>
      scala.io.StdIn.readLine() match {
        case "create table" => MemberDAO.createTable()
        case "register" =>
          println("What does he call?")
          MemberDAO.createBy(scala.io.StdIn.readLine())
        case "search" =>
          println("Who you want to search? please input name")
          val fetchedMember = MemberDAO.selectBy(scala.io.StdIn.readLine())
          println(Json.toJson(fetchedMember))
        case "update" =>
          println("Which user you want to update? please input id")
          val fetchedMember = MemberDAO.selectBy(scala.io.StdIn.readInt())
          fetchedMember match {
            case Some(value) =>
              println("How do you want to call?")
              MemberDAO.update(value.id)(scala.io.StdIn.readLine())
            case None => println("The user does not exist.")
          }
        case "drop table" => MemberDAO.dropTable()
        case _ => println("The operation does not supported.")
      }
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

  def selectAll()(using session: DBSession): List[MemberDTO] =
    sql"select * from members"
      .map(MemberDTO.*).list.apply()

  def selectBy(id: Int)(using session: DBSession): Option[MemberDTO] =
    sql"""
         select * from members
         where
           id = ${id}
   """.map(MemberDTO.*).single.apply()

  def selectBy(name: String)(using session: DBSession): Option[MemberDTO] =
    sql"""
         select * from members
         where
           name = ${name}
         limit 1
   """.map(MemberDTO.*).single.apply()

  def update(id: Long)(name: String)(using session: DBSession): Unit =
    sql"""
         update members set name = ${name} where id = ${id}
   """.execute.apply()

  def createTable()(using session: DBSession): Unit =
    sql"""
          create table members (
            id serial not null primary key,
            name varchar(64),
            created_at timestamp not null
    )""".execute.apply()

  def createBy(name: String)(using session: DBSession): Unit =
    sql"""
         insert into members (
            name,
            created_at)
         values (
            ${name},
            current_timestamp)
         """.update.apply()

  def dropTable()(using session: DBSession): Unit =
      sql"""drop table members""".execute.apply()
}


