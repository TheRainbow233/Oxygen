package cn.rainbow.oxygen.command.sender

import cn.rainbow.oxygen.command.logger.ConsoleCommandLogger
import cn.rainbow.oxygen.Oxygen
import cn.rainbow.oxygen.command.logger.CMinecraftLogger
import cn.rainbow.oxygen.command.logger.CommandLogger

object CommandSender {

    fun forMinecraft(command: String) {
        Oxygen.INSTANCE.commandManager.parser.run(command, CMinecraftLogger())
    }
    fun forCmd(command: String) {
        Oxygen.INSTANCE.commandManager.parser.run(command, ConsoleCommandLogger.COMMAND_LOGGER)
    }

    fun send(command: String, commandLogger: CommandLogger) {
        Oxygen.INSTANCE.commandManager.parser.run(command, commandLogger)
    }
}