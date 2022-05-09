package org.jetbrains.bsp.bazel.workspacecontext

import ch.epfl.scala.bsp4j.BuildTargetIdentifier
import io.kotest.matchers.shouldBe
import io.vavr.collection.List
import org.jetbrains.bsp.bazel.executioncontext.api.ProjectViewToExecutionContextEntityMapperException
import org.jetbrains.bsp.bazel.projectview.model.ProjectView
import org.jetbrains.bsp.bazel.projectview.model.sections.ProjectViewTargetsSection
import org.junit.jupiter.api.Test

class TargetsSpecMapperTest {

    @Test
    fun `should return success with default spec if targets are null`() {
        // given
        val projectView = ProjectView.Builder(targets = null).build().get()

        // when
        val targetsSpecTry = TargetsSpecMapper.map(projectView)

        // then
        targetsSpecTry.isSuccess shouldBe true
        val targetsSpec = targetsSpecTry.get()

        val expectedTargetsSpec = TargetsSpec(listOf(BuildTargetIdentifier("//...")), emptyList())
        targetsSpec shouldBe expectedTargetsSpec
    }

    @Test
    fun `should return success and default if targets have no values`() {
        // given
        val projectView = ProjectView.Builder(
            targets = ProjectViewTargetsSection(
                List.of(),
                List.of()
            )
        ).build().get()

        // when
        val targetsSpecTry = TargetsSpecMapper.map(projectView)

        // then
        targetsSpecTry.isSuccess shouldBe true
        val targetsSpec = targetsSpecTry.get()

        val expectedTargetsSpec = TargetsSpec(listOf(BuildTargetIdentifier("//...")), emptyList())
        targetsSpec shouldBe expectedTargetsSpec
    }

    @Test
    fun `should return fail if targets have no included values`() {
        // given
        val projectView = ProjectView.Builder(
            targets = ProjectViewTargetsSection(
                List.of(),
                List.of(
                    BuildTargetIdentifier("//excluded_target1"),
                    BuildTargetIdentifier("//excluded_target2"),
                )
            )
        ).build().get()

        // when
        val targetsSpecTry = TargetsSpecMapper.map(projectView)

        // then
        targetsSpecTry.isFailure shouldBe true
        targetsSpecTry.cause::class shouldBe ProjectViewToExecutionContextEntityMapperException::class
        targetsSpecTry.cause.message shouldBe "Mapping project view into 'targets' failed! 'targets' section has no included targets."
    }

    @Test
    fun `should return success for successful mapping`() {
        // given
        val projectView =
            ProjectView.Builder(
                targets = ProjectViewTargetsSection(
                    List.of(
                        BuildTargetIdentifier("//included_target1"),
                        BuildTargetIdentifier("//included_target2"),
                        BuildTargetIdentifier("//included_target3")
                    ),
                    List.of(
                        BuildTargetIdentifier("//excluded_target1"),
                        BuildTargetIdentifier("//excluded_target2"),
                    ),
                )
            ).build().get()

        // when
        val targetsSpecTry = TargetsSpecMapper.map(projectView)

        // then
        targetsSpecTry.isSuccess shouldBe true
        val targetsSpec = targetsSpecTry.get()

        val expectedTargets =
            TargetsSpec(
                listOf(
                    BuildTargetIdentifier("//included_target1"),
                    BuildTargetIdentifier("//included_target2"),
                    BuildTargetIdentifier("//included_target3"),
                ),
                listOf(
                    BuildTargetIdentifier("//excluded_target1"),
                    BuildTargetIdentifier("//excluded_target2"),
                ),
            )
        targetsSpec shouldBe expectedTargets
    }
}