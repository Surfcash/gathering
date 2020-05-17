package gg.warcraft.gathering.spigot

import java.util.logging.Logger

import gg.warcraft.gathering.GatheringSpotService
import gg.warcraft.gathering.gatherable.{ BlockGatherableService, EntityGatherableRepository, EntityGatherableService }
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.spigot.implicits._
import gg.warcraft.monolith.api.core.task.TaskService
import io.getquill.{ SnakeCase, SqliteDialect }
import io.getquill.context.jdbc.JdbcContext
import org.bukkit.Server
import org.bukkit.plugin.Plugin

object implicits {
  private[spigot] type DatabaseContext = JdbcContext[SqliteDialect, SnakeCase]

  private var _server: Server = _
  private var _plugin: Plugin = _
  private var _logger: Logger = _

  private var _database: DatabaseContext = _
  private var _eventService: EventService = _
  private var _taskService: TaskService = _

  private[spigot] def init()(implicit
      server: Server,
      plugin: Plugin,
      logger: Logger,
      database: DatabaseContext,
      eventService: EventService,
      taskService: TaskService
  ): Unit = {
    _server = server
    _plugin = plugin
    _logger = logger
    _database = database
    _eventService = eventService
    _taskService = taskService
  }

  // Spigot
  private implicit lazy val server: Server = _server
  private implicit lazy val plugin: Plugin = _plugin
  private implicit lazy val logger: Logger = _logger

  // Core
  private implicit lazy val database: DatabaseContext = _database
  private implicit lazy val eventService: EventService = _eventService
  private implicit lazy val taskService: TaskService = _taskService

  // Gathering
  implicit val gatheringSpotService: GatheringSpotService =
    new GatheringSpotService
  implicit val entityGatherableRepository: EntityGatherableRepository =
    new EntityGatherableRepository
  implicit val entityGatherableService: EntityGatherableService =
    new EntityGatherableService
  implicit val blockGatherableService: BlockGatherableService =
    new BlockGatherableService
}
