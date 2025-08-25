// fullstack-book/docusaurus.config.js
// @ts-check

import { themes as prismThemes } from 'prism-react-renderer';
import path from 'path';
import fs from 'fs';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// ---- External (private) content locations for local dev ----
const EXTERNAL_ROOT = path.resolve(__dirname, '../../fullstack-book-source');
const EXTERNAL_DOCS = path.join(EXTERNAL_ROOT, 'docs');
const EXTERNAL_BLOG = path.join(EXTERNAL_ROOT, 'blog');
const EXTERNAL_STATIC = path.join(EXTERNAL_ROOT, 'static');

/**
 * Use external sources only when:
 *  - LOCAL_DOCS=external (so CI won't), and
 *  - the external paths actually exist
 */
const USE_EXTERNAL =
  process.env.LOCAL_DOCS === 'external' &&
  fs.existsSync(EXTERNAL_DOCS) &&
  fs.existsSync(EXTERNAL_STATIC);

console.log(
  `[docusaurus] Using ${USE_EXTERNAL ? 'EXTERNAL' : 'IN-REPO'} content ` +
  `(LOCAL_DOCS=${process.env.LOCAL_DOCS ?? 'unset'})`
);

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Full Stack Development with Spring Boot and React',
  tagline: 'A Comprehensive Hands-On Guide for Beginners and Intermediate Developers',
  future: { v4: true },

  // GitHub Pages
  url: 'https://brianronock.github.io',
  baseUrl: '/fullstackdev_spring-and-react/',
  organizationName: 'brianronock',
  projectName: 'fullstackdev_spring-and-react',

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  i18n: { defaultLocale: 'en', locales: ['en'] },

  // Use external static dir during local dev, otherwise the in-repo one
  staticDirectories: USE_EXTERNAL ? [EXTERNAL_STATIC] : ['static'],

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          // External for local dev, in-repo for CI/prod
          path: USE_EXTERNAL ? EXTERNAL_DOCS : 'docs',
          routeBasePath: 'docs',
          sidebarPath: './sidebars.js',
          exclude: ['**/.gitbook/**', '**/_**/**', '**/*.backup.md'],
          // editUrl: 'https://github.com/brianronock/fullstackdev_spring-and-react/edit/main/fullstack-book/',
        },

        // Keep blog off until we’re ready—enable conditionally if present
        blog: fs.existsSync(USE_EXTERNAL ? EXTERNAL_BLOG : path.resolve(__dirname, 'blog'))
          ? {
            path: USE_EXTERNAL ? EXTERNAL_BLOG : 'blog',
            routeBasePath: 'blog',
            onUntruncatedBlogPosts: 'ignore', // suppress warning

          }
          : false,

        theme: { customCss: './src/css/custom.css' },
      }),
    ],
  ],

  // Watch external paths in dev so HMR picks up changes
  plugins: [
    USE_EXTERNAL && function watchExternal() {
      return {
        name: 'watch-external',
        getPathsToWatch() {
          return [
            path.join(EXTERNAL_DOCS, '**/*'),
            path.join(EXTERNAL_STATIC, '**/*'),
            fs.existsSync(EXTERNAL_BLOG) ? path.join(EXTERNAL_BLOG, '**/*') : null,
          ].filter(Boolean);
        },
      };
    },
  ].filter(Boolean),

  themeConfig: {
    image: 'img/social-card.png',
    favicon: 'img/logo.svg',
    navbar: {
      title: 'Full Stack Book',
      logo: { alt: 'Book Logo', src: 'img/logo.svg' },
      items: [
        { type: 'docSidebar', sidebarId: 'tutorialSidebar', position: 'left', label: 'Table of Contents' },
        { href: 'https://github.com/brianronock/fullstackdev_spring-and-react', label: 'GitHub', position: 'right' },
      ],
    },
    footer: {
      style: 'dark',
      copyright: `© ${new Date().getFullYear()} Brian Rono CK`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
      additionalLanguages: ['java', 'bash', 'json', 'yaml', 'sql', 'http', 'docker', 'properties', 'javascript', 'jsx'],
    },
  },
};

export default config;