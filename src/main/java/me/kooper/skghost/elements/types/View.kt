package me.kooper.skghost.elements.types

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.expressions.base.EventValueExpression
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.registrations.Classes
import me.kooper.ghostcore.models.ChunkedView

class View {

    companion object {
        init {
            Classes.registerClass(
                ClassInfo(ChunkedView::class.java, "view").user("views?").name("View")
                    .description("Represents a view from GhostCore.")
                    .examples("on view create:", "broadcast \"%event-view%\"")
                    .defaultExpression(EventValueExpression(ChunkedView::class.java))
                    .parser(object : Parser<ChunkedView>() {
                        override fun parse(input: String?, context: ParseContext?): ChunkedView? {
                            return null
                        }

                        override fun canParse(context: ParseContext?): Boolean {
                            return false
                        }

                        override fun toVariableNameString(view: ChunkedView?): String {
                            if (view == null) return ""
                            return view.name
                        }

                        override fun toString(view: ChunkedView?, flags: Int): String {
                            return toVariableNameString(view)
                        }
                    })
            )
        }
    }

}