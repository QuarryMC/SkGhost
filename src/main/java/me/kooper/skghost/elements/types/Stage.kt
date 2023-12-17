package me.kooper.skghost.elements.types

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.expressions.base.EventValueExpression
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.registrations.Classes
import me.kooper.ghostcore.models.Stage
import me.kooper.skghost.SkGhost

class Stage {

    companion object {
        init {
            Classes.registerClass(
                ClassInfo(Stage::class.java, "stage").user("stages?").name("Stage")
                    .description("Represents a stage from GhostCore.")
                    .examples("on stage create:", "broadcast \"%event-stage%\"")
                    .defaultExpression(EventValueExpression(Stage::class.java))
                    .parser(object : Parser<Stage>() {
                        override fun parse(input: String?, context: ParseContext?): Stage? {
                            if (input == null || SkGhost.instance.ghostCore.stageManager.getStage(input) == null) return null
                            return SkGhost.instance.ghostCore.stageManager.getStage(input)!!
                        }

                        override fun canParse(context: ParseContext?): Boolean {
                            return true
                        }

                        override fun toVariableNameString(stage: Stage?): String {
                            if (stage == null) return ""
                            return stage.name
                        }

                        override fun toString(stage: Stage?, flags: Int): String {
                            return toVariableNameString(stage)
                        }
                    })
            )
        }
    }

}