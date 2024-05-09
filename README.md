# Telegram Bot

This Telegram bot allows users to interact with various APIs to discover stuffs.
Try out this bot : Here(https://t.me/TetaNewsBot)

## Commands

- `/info`: Interact with Coinmarketcap API to get information about a specific cryptocurrency.
- `/p2p`: Interact with Coingecko API to get information about USDT/VND rate .
- `/generate`: Generate an image from text using LimeWire AI API.
- `/shorten`: Shorten given link using spoo.me.
- Still updating

## Setup

1. **Clone the Repository:**
```
git clone https://github.com/teta0412/telegram-bot.git
cd telegram-bot
```
2. **Install Dependencies:**
```
dependencies {
    implementation ("org.telegram:telegrambots:6.9.7.0")
    implementation ("org.telegram:telegrambots-abilities:6.9.7.0")
    implementation ("org.json:json:20090211")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}
```
3. **Set up Telegram Bot:**
- Create a new bot with BotFather on Telegram and obtain your bot token.
- Replace `YOUR_BOT_TOKEN` in `BotConfig.java` with your actual bot token.

4. **Run the Bot:**
