package app.revanced.patches.youtube.player.action

import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.shared.conversionContextFingerprintToString2
import app.revanced.patches.shared.litho.addLithoFilter
import app.revanced.patches.shared.litho.componentContextSubParserFingerprint2
import app.revanced.patches.shared.litho.lithoFilterPatch
import app.revanced.patches.youtube.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.revanced.patches.youtube.utils.extension.Constants.COMPONENTS_PATH
import app.revanced.patches.youtube.utils.extension.Constants.PLAYER_PATH
import app.revanced.patches.youtube.utils.fix.litho.lithoLayoutPatch
import app.revanced.patches.youtube.utils.patch.PatchList.HIDE_ACTION_BUTTONS
import app.revanced.patches.youtube.utils.playservice.is_20_20_or_greater
import app.revanced.patches.youtube.utils.request.buildRequestPatch
import app.revanced.patches.youtube.utils.request.hookBuildRequest
import app.revanced.patches.youtube.utils.settings.ResourceUtils.addPreference
import app.revanced.patches.youtube.utils.settings.settingsPatch
import app.revanced.patches.youtube.video.information.videoInformationPatch
import app.revanced.util.*
import app.revanced.util.fingerprint.methodOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

private const val FILTER_CLASS_DESCRIPTOR =
    "$COMPONENTS_PATH/ActionButtonsFilter;"
private const val ACTION_BUTTONS_CLASS_DESCRIPTOR =
    "$PLAYER_PATH/ActionButtonsPatch;"

@Suppress("unused")
val actionButtonsPatch = bytecodePatch(
    HIDE_ACTION_BUTTONS.title,
    HIDE_ACTION_BUTTONS.summary,
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(
        settingsPatch,
        lithoFilterPatch,
        lithoLayoutPatch,
        videoInformationPatch,
        buildRequestPatch,
    )

    execute {
        addLithoFilter(FILTER_CLASS_DESCRIPTOR)

        // region patch for hide action buttons by index

        if (!is_20_20_or_greater) {
            componentListFingerprint.methodOrThrow(componentContextSubParserFingerprint2).apply {
                val identifierReference = with(conversionContextFingerprintToString2.methodOrThrow()) {
                    val identifierStringIndex =
                        indexOfFirstStringInstructionOrThrow(", identifierProperty=")
                    val identifierStringAppendIndex =
                        indexOfFirstInstructionOrThrow(identifierStringIndex, Opcode.INVOKE_VIRTUAL)
                    val identifierStringAppendIndexRegister =
                        getInstruction<FiveRegisterInstruction>(identifierStringAppendIndex).registerD
                    val identifierAppendIndex =
                        indexOfFirstInstructionOrThrow(
                            identifierStringAppendIndex + 1,
                            Opcode.INVOKE_VIRTUAL
                        )
                    val identifierRegister =
                        getInstruction<FiveRegisterInstruction>(identifierAppendIndex).registerD
                    val identifierIndex =
                        indexOfFirstInstructionReversedOrThrow(identifierAppendIndex) {
                            opcode == Opcode.IGET_OBJECT &&
                                    getReference<FieldReference>()?.type == "Ljava/lang/String;" &&
                                    (this as? TwoRegisterInstruction)?.registerA == identifierRegister
                        }
                    getInstruction<ReferenceInstruction>(identifierIndex).reference
                }

                val listIndex = implementation!!.instructions.lastIndex
                val listRegister = getInstruction<OneRegisterInstruction>(listIndex).registerA
                val identifierRegister = listRegister + 1

                addInstructionsAtControlFlowLabel(
                    listIndex, """
                    move-object/from16 v$identifierRegister, p2
                    iget-object v$identifierRegister, v$identifierRegister, $identifierReference
                    invoke-static {v$listRegister, v$identifierRegister}, $ACTION_BUTTONS_CLASS_DESCRIPTOR->hideActionButtonByIndex(Ljava/util/List;Ljava/lang/String;)Ljava/util/List;
                    move-result-object v$listRegister
                    """
                )
            }
        }

        hookBuildRequest("$ACTION_BUTTONS_CLASS_DESCRIPTOR->fetchStreams(Ljava/lang/String;Ljava/util/Map;)V")

        // endregion

        // region add settings

        addPreference(
            arrayOf(
                "PREFERENCE_SCREEN: PLAYER",
                "SETTINGS: HIDE_ACTION_BUTTONS"
            ),
            HIDE_ACTION_BUTTONS
        )

        // endregion

    }
}
