package org.jetbrains.bsp.bazel.workspacecontext

import io.kotest.matchers.shouldBe
import org.jetbrains.bsp.bazel.projectview.model.ProjectView
import org.jetbrains.bsp.bazel.projectview.model.sections.ProjectViewBazelPathSection
import org.junit.jupiter.api.Test
import kotlin.io.path.Path


class BazelPathSpecMapperTest {

    // TODO https://youtrack.jetbrains.com/issue/BAZEL-58 + framework to test envs
//    @Test
//    fun `should return failure if it isn't possible to deduct bazel path from PATH and bazel path is null`() {
//       // given
//       val projectView = ProjectView.Builder(bazelPath = null).build().get()
//
//       // when
//       val bazelPathSpecTry = BazelPathSpecMapper.map(projectView)
//
//       // then
//       bazelPathSpecTry.isFailure shouldBe true
//       bazelPathSpecTry.cause::cause shouldBe ProjectViewToExecutionContextEntityMapperException::class
//       bazelPathSpecTry.cause.message shouldBe "Mapping project view into 'bazel path' failed! Could not find bazel on your PATH"
//    }

    @Test
    fun `should return success with deducted bazel path from PATH if bazel path is null`() {
        // given
        val projectView = ProjectView.Builder(bazelPath = null).build().get()

        // when
        val bazelPathSpecTry = BazelPathSpecMapper.map(projectView)

        // then
        bazelPathSpecTry.isSuccess shouldBe true
        val bazelPathSpec = bazelPathSpecTry.get()

        val expectedBazelPathSpec = BazelPathSpec(Path("/usr/local/bin/bazel"))
        bazelPathSpec shouldBe expectedBazelPathSpec
    }

    @Test
    fun `should return success for successful mapping`() {
        // given
        val projectView =
            ProjectView.Builder(
                bazelPath = ProjectViewBazelPathSection(Path("/path/to/bazel"))
            ).build().get()

        // when
        val bazelPathSpecTry = BazelPathSpecMapper.map(projectView)

        // then
        bazelPathSpecTry.isSuccess shouldBe true
        val bazelPathSpec = bazelPathSpecTry.get()

        val expectedBazelPathSpec = BazelPathSpec(Path("/path/to/bazel"))
        bazelPathSpec shouldBe expectedBazelPathSpec
    }
}
