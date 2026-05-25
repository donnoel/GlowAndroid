# AGENTS.project.md

# Glow Daily Practice for Android Project Guide for Agents

## Product intent

Glow Daily Practice for Android is an Android version of Glow focused on calm habit tracking, low-friction check-ins, and clear progress feedback.
Success means users can review today's habits, mark progress, add useful habit templates, and understand momentum through an interface that feels native on Android.

## Current product phase (implemented baseline)

1) Core scope
- Material 3 app shell with Today, Insights, and Library bottom-navigation sections
- Today screen with seeded habits, completion toggles, and a completion summary
- Insights screen with derived completion, streak, and recent-activity feedback
- Library screen with suggested habits that can be added to Today
- In-memory state only; persistence, accounts, reminders, and store release are not implemented

2) Architecture boundaries
- Compose screens handle presentation and user interaction
- `GlowApp` owns the current tab and shared in-memory `HabitStore`
- `HabitStore` owns habit mutation and seed data
- `HabitItem` defines the current habit model

3) Reliability and UX goals
- Clean build with no warnings
- Keep Today, Insights, and Library state consistent during a running session
- Preserve a calm, readable Material 3 interface
- Keep the app honest about its in-development state until persistence and release work exist

4) Testing priorities
- Habit completion toggle behavior
- Adding library templates into Today's list
- Derived progress and Insights calculations
- Navigation among Today, Insights, and Library

## Architecture snapshot (current)

- App entry and tab shell: `app/src/main/java/com/example/glow/MainActivity.kt`
- Model: `app/src/main/java/com/example/glow/HabitItem.kt`
- State store: `app/src/main/java/com/example/glow/HabitStore.kt`
- Screens:
  - `app/src/main/java/com/example/glow/TodayScreen.kt`
  - `app/src/main/java/com/example/glow/InsightsScreen.kt`
  - `app/src/main/java/com/example/glow/LibraryScreen.kt`
- Theme: `app/src/main/java/com/example/glow/ui/theme/`

## Behavior invariants (do not regress)

- Tapping a Today completion control updates progress and Insights from the same habit state.
- Selecting a Library template adds it to Today and returns the user to the Today tab.
- The seeded sample habits appear only when the in-memory store begins empty.
- Today, Insights, and Library remain the primary navigation sections.
- Do not claim persistence, sync, reminders, or Play Store availability unless implemented.

## UX rules

- Keep primary flows fast: check in, view progress, and add a habit.
- Preserve readable, content-first hierarchy with restrained visual treatment.
- Use appropriate Compose semantics and accessible interaction targets for user-facing changes.

## Coding conventions

- Prefer small composables and explicit state ownership.
- Move derived or mutating logic into focused state/domain types when it grows beyond simple screen presentation.
- Keep user-visible wording calm, clear, and accurate to implemented behavior.

## Build/run notes

- Project module: `app`
- Minimum Android API: 29
- Build command:
  - `./gradlew assembleDebug`
- Unit test command:
  - `./gradlew test`

## Near-term priorities

- Add durable habit persistence.
- Replace sample-only flows with user-created habit management.
- Expand automated coverage for state and navigation behavior.

## Output expectations per patch

Provide:
- Summary of change
- Files modified
- Any migration considerations
- Commit message suggestion
