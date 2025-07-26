# 🎵 Voice-Over Translation Patches

Автоматический голосовой перевод видео для YouTube с использованием ReVanced Manager.

## ✨ Возможности

- 🎵 **Автоматический голосовой перевод** видео YouTube
- ⚙️ **Интеграция с настройками** YouTube
- 🌍 **Поддержка множества языков** (русский, английский, испанский, и др.)
- 🔧 **Совместимость с RVX Manager** и ReVanced Manager

## 📱 Поддерживаемые версии YouTube

- 19.05.36
- 19.16.39  
- 19.43.41
- 19.44.39
- 19.47.53
- 20.30.35

## 🚀 Использование

### Автоматическая сборка (рекомендуется)

1. **Добавьте источник в RVX Manager:**
   ```
   https://api.github.com/repos/Bloodrabbid/revanced-patches/releases/latest
   ```

2. **Выберите YouTube APK** для патчинга

3. **Найдите и включите** патч "Voice-Over Translation"

4. **Соберите APK** и установите

### Ручная сборка

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/Bloodrabbid/revanced-patches.git
   cd revanced-patches
   ```

2. **Соберите патчи:**
   ```bash
   ./gradlew clean build
   ```

3. **Найдите готовые файлы в:**
   - `build/libs/voice-over-translation-patches.jar`
   - `patches.json`

## ⚙️ Настройки в YouTube

После установки пропатченного YouTube:

1. Откройте **YouTube**
2. Перейдите в **Настройки** → **Voice-Over Translation**
3. Настройте:
   - ✅ Включить голосовой перевод
   - 🌍 Целевой язык перевода
   - 🔄 Автоматический перевод
   - 🔔 Показывать уведомления

## 🛠️ Сборка через GitHub Actions

Этот репозиторий использует GitHub Actions для автоматической сборки:

- **При push в main** - создает артефакты сборки
- **При создании тега** - автоматически создает релиз
- **Manual trigger** - можно запустить сборку вручную

## 🌍 Поддерживаемые языки

- 🇷🇺 Русский (ru)
- 🇺🇸 English (en)  
- 🇪🇸 Español (es)
- 🇫🇷 Français (fr)
- 🇩🇪 Deutsch (de)
- 🇮🇹 Italiano (it)
- 🇵🇹 Português (pt)
- 🇨🇳 中文 (zh)
- 🇯🇵 日本語 (ja)
- 🇰🇷 한국어 (ko)

## 📁 Структура релиза

Каждый релиз содержит:

- **patches.json** - Метаданные патчей для ReVanced Manager
- **patches.rvp** - Скомпилированные патчи и ресурсы

## 🔧 Требования

- **ReVanced Manager** или **RVX Manager**
- **YouTube APK** совместимой версии
- **Android 7.0+** (API level 24+)

## 📄 Лицензия

Этот проект распространяется под лицензией MIT.

## 🤝 Вклад в проект

Приветствуются вклады в развитие проекта! Создавайте Issues и Pull Requests.

## ⚡ Быстрый старт

1. Скачайте **RVX Manager**
2. Добавьте источник: `https://api.github.com/repos/Bloodrabbid/revanced-patches/releases/latest`
3. Выберите **YouTube APK** и патч **"Voice-Over Translation"**
4. Соберите и наслаждайтесь! 🎉