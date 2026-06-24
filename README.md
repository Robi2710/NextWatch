# NextWatch

NextWatch is a clean and simple app for keeping track of movies and TV shows you want to watch next. It helps you organize titles, save favorites, and quickly return to the content that caught your attention.

## Features

- Search for movies and TV shows
- Save titles to a personal watchlist
- Keep track of content you want to watch later
- View useful details about each title
- Responsive interface for desktop and mobile
- Lightweight, friendly user experience

## Tech Stack

Update this section with the exact tools used in the project:

- Frontend: React / Next.js / Vite
- Styling: CSS / Tailwind CSS / CSS Modules
- API: TMDB API or another movie database provider
- State management: React state / Context / local storage
- Deployment: Vercel / Netlify / GitHub Pages

## Getting Started

### Prerequisites

Make sure you have installed:

- Node.js
- npm, yarn, or pnpm

### Installation

Clone the repository:

```bash
git clone https://github.com/Robi2710/NextWatch.git
cd NextWatch
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Open the local URL shown in the terminal, usually:

```text
http://localhost:5173
```

or, for Next.js projects:

```text
http://localhost:3000
```

## Environment Variables

If the project uses an external movie API, create a `.env` file in the root directory and add your API key:

```env
VITE_TMDB_API_KEY=your_api_key_here
```

For Next.js, the variable may look like this:

```env
NEXT_PUBLIC_TMDB_API_KEY=your_api_key_here
```

Adjust the variable name to match the implementation.

## Project Structure

```text
NextWatch/
├── public/
├── src/
│   ├── components/
│   ├── pages/
│   ├── hooks/
│   ├── services/
│   └── styles/
├── package.json
└── README.md
```

## Available Scripts

Depending on the setup, the project may include:

```bash
npm run dev
```

Runs the app in development mode.

```bash
npm run build
```

Builds the app for production.

```bash
npm run preview
```

Previews the production build locally.

```bash
npm run lint
```

Checks the code for linting issues.

## Roadmap

- Add authentication
- Sync watchlists across devices
- Add categories such as Watching, Watched, and Favorites
- Add ratings and personal notes
- Improve filtering and sorting
- Add recommendations based on saved titles

## Contributing

Contributions are welcome. To contribute:

1. Fork the repository
2. Create a new branch
3. Make your changes
4. Commit your work
5. Open a pull request
