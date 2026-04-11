[System.IO.File]::AppendAllText("build.gradle.kts", "`n")
git add build.gradle.kts
git commit -m "BLDS"

[System.IO.File]::AppendAllText("settings.gradle.kts", "`n")
git add settings.gradle.kts
git commit -m "SETS"

[System.IO.File]::AppendAllText("gradle.properties", "`n")
git add gradle.properties
git commit -m "PROP"

[System.IO.File]::AppendAllText("gradlew", " ")
git add gradlew
git commit -m "EXEC"

[System.IO.File]::AppendAllText("gradlew.bat", " ")
git add gradlew.bat
git commit -m "EXEC"

[System.IO.File]::AppendAllText("gradle\wrapper\gradle-wrapper.properties", "`n")
git add gradle\wrapper\gradle-wrapper.properties
git commit -m "WRAP"

[System.IO.File]::AppendAllText(".idea\misc.xml", "`n")
git add .idea
git commit -m "IDES"

[System.IO.File]::AppendAllText(".gitignore", "`n")
git add .gitignore
git commit -m "IGNR"

git add README.md
git commit -m "INFO"

git add app
git commit -m "CODE"

git push
