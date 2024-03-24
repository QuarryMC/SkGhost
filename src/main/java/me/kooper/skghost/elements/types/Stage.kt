package me.kooper.skghost.elements.types

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.expressions.base.EventValueExpression
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.registrations.Classes
import me.kooper.ghostcore.models.ChunkedStage
import me.kooper.skghost.SkGhost

class Stage {

    companion object {
        init {
            Classes.registerClass(
                ClassInfo(ChunkedStage::class.java, "stage").user("stages?").name("Stage")
                    .description("Represents a stage from GhostCore.")
                    .examples("on stage create:", "broadcast \"%event-stage%\"")
                    .defaultExpression(EventValueExpression(ChunkedStage::class.java))
                    .parser(object : Parser<ChunkedStage>() {
                        override fun parse(input: String?, context: ParseContext?): ChunkedStage? {
                            if (input == null || SkGhost.instance.ghostCore.stageManager.getStage(input) == null) return null
                            return SkGhost.instance.ghostCore.stageManager.getStage(input)!! as ChunkedStage
                        }

                        override fun canParse(context: ParseContext?): Boolean {
                            return true
                        }

                        override fun toVariableNameString(stage: ChunkedStage?): String {
                            if (stage == null) return ""
                            return stage.name
                        }

                        override fun toString(stage: ChunkedStage?, flags: Int): String {
                            return toVariableNameString(stage)
                        }
                    })
            )
        }
    }

}