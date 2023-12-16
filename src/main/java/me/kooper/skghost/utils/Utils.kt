package me.kooper.skghost.utils

import org.bukkit.Material
import org.bukkit.block.data.BlockData

object Utils {

    fun parseMaterialValues(input: String): Map<BlockData, Double> {
        val materialValueMap = mutableMapOf<BlockData, Double>()
        val entries = input.split("|")
        for (entry in entries) {
            val (materialName, valueStr) = entry.split(":")
            val blockData = Material.getMaterial(materialName)!!.createBlockData()
            val value = valueStr.toDoubleOrNull()
            if (value != null) {
                materialValueMap[blockData] = value
            }
        }
        return materialValueMap
    }

    fun createMaterialValuesString(materialValueMap: Map<BlockData, Double>): String {
        val materialValuesList = mutableListOf<String>()
        for ((blockData, value) in materialValueMap) {
            materialValuesList.add("${blockData.material}:$value")
        }
        return materialValuesList.joinToString("|")
    }

}