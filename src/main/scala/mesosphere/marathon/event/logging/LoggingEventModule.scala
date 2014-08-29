package mesosphere.marathon.event.logging

import scala.language.postfixOps
import com.google.inject.{ Scopes, Singleton, Provides, AbstractModule }
import akka.actor._
import akka.pattern.ask
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import org.rogach.scallop.ScallopConf
import org.apache.log4j.Logger
import scala.concurrent.duration._
import akka.util.Timeout
import org.apache.mesos.state.State
import mesosphere.marathon.state.MarathonStore
import mesosphere.marathon.Main
import mesosphere.marathon.event.{ MarathonSubscriptionEvent, Subscribe }
import akka.event.EventStream
import mesosphere.marathon.event.{ EventModule, EventSubscriber, MarathonEvent }
import javax.inject.{ Named, Inject }

/*
Should be able to use this by including `--event_subscriber logging` option when starting Marathon.
*/

trait LoggingEventConfiguration extends ScallopConf {
  //no configuration needed for logging event subscriber :D
}

class LoggingEventModule extends AbstractModule {
  def configure() {
    bind(classOf[LoggingEventSubscriber]).asEagerSingleton()
  }

  @Provides
  @Named(LoggingEventModule.StatusUpdateActor)
  def provideStatusUpdateActor(system: ActorSystem): ActorRef = {
    system.actorOf(Props(new LoggingEventActor))
  }
}

object LoggingEventModule {
  final val StatusUpdateActor = "EventsActor"

  val executorService = Executors.newCachedThreadPool()
  val executionContext = ExecutionContext.fromExecutorService(executorService)

  val timeout = Timeout(10 seconds)
}

class LoggingEventSubscriber @Inject() (
  @Named(LoggingEventModule.StatusUpdateActor) val actor: ActorRef,
  @Named(EventModule.busName) val eventBus: EventStream)
    extends EventSubscriber[LoggingEventConfiguration, LoggingEventModule] {

  eventBus.subscribe(actor, classOf[MarathonEvent])

  def configuration() = {
    classOf[LoggingEventConfiguration]
  }

  def module() = {
    Some(classOf[LoggingEventModule])
  }
}

class LoggingEventActor extends Actor with ActorLogging {
  implicit val ec = LoggingEventModule.executionContext
  implicit val timeout = LoggingEventModule.timeout

  def receive = {
    case event: MarathonEvent =>
      broadcast(event)
    case _ =>
      log.warning("Message not understood!")
  }

  def broadcast(event: MarathonEvent): Unit = {
    log.info("Received Marathon event: {}", event)
  }
}
