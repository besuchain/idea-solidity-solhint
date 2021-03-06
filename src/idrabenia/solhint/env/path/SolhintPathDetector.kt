package idrabenia.solhint.env.path

import java.io.File


object SolhintPathDetector : BasePathDetector() {

    private val solhintName = "solhint"

    fun detectSolhintPath(nodePath: String) =
        solhintForNode(nodePath) ?: firstSolhintFromEnv() ?: ""

    fun solhintForNode(nodePath: String) =
        SolhintCmd(solhintCmdSiblingToNode(nodePath)).pathToSolhintJs()

    fun firstSolhintFromEnv() =
        detectAllSolhintPaths().firstOrNull()

    fun detectAllSolhintPaths() =
        detectAllInPaths(solhintName)
            .map { SolhintCmd(it).pathToSolhintJs() }
            .filterNotNull()

    private fun solhintCmdSiblingToNode(nodePath: String) =
        detectWithFilter(solhintName, { it.parent == File(nodePath).parent })

}
