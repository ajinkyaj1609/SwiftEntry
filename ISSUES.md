# SwiftEntry issue tracker

Reviewed and addressed on 2026-09-25. The code changes are local to this workspace.

| # | Issue | Status | Resolution |
|---|---|---|---|
| 1 | SMTP credential committed in source | Code fixed; account action required | `TLSEmail.java` now reads `SWIFTENTRY_SMTP_USERNAME` and `SWIFTENTRY_SMTP_PASSWORD` from the environment. Revoke the exposed app password and create a replacement. The old value remains in repository history until the owner handles that history separately. |
| 2 | Attendance CSV can be truncated during a save | Fixed | `StudentCsv.write` writes a complete temporary file and atomically replaces the old file only after a successful write. Save errors reach the caller. |
| 3 | New student email is lost after restart | Fixed | The email is assigned before the student list is persisted. |
| 4 | Tardy cutoff differs from the teacher's class start time | Fixed | The selected start time is passed to each student's scan tracker. |
| 5 | Duplicate IDs cause contradictory attendance | Fixed | New duplicate IDs are rejected. Existing duplicate rows are merged by ID when loaded: the highest count in each numeric column and a nonempty email are kept. Conflicting names for one ID stop loading so they can be resolved manually. The checked-in sample CSV remains unchanged until the next save. |
| 6 | Removing an unknown ID crashes the menu | Fixed | The menu reports an unknown ID and keeps running. |
| 7 | Commas and quotes break student CSV records | Fixed | CSV fields are escaped on write and parsed on read, including quoted newlines. |
| 8 | Invalid class time input terminates the program | Fixed | Invalid times prompt again, and the end time must follow the start time on the same day. |
| 9 | Test email prints success after a failed send | Fixed | Sending errors reach the caller; `TestEmail` reports failure and exits unsuccessfully. Set `SWIFTENTRY_TEST_EMAIL` to a test recipient to run it. |

`SwiftEntryTests.java` covers CSV round trips, failed writes, duplicate merging, add/remove persistence, invalid time input, and scan cutoff behavior. It does not send live email.
