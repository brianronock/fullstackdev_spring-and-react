// @ts-check
import { themes as prismThemes } from 'prism-react-renderer';

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Full Stack Development with Spring Boot and React',
  tagline: 'A Comprehensive Hands-On Guide for Beginners and Intermediate Developers',
  future: { v4: true },

  // For local dev this can be anything, Docusaurus ignores it for `npm run start`
  url: 'https://brianronock.github.io',
  // When deploying to GitHub Pages under a repo:
  baseUrl: '/fullstackdev_spring-and-react/',

  // GH Pages deployment config
  organizationName: 'brianronock',
  projectName: 'fullstackdev_spring-and-react',

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  i18n: { defaultLocale: 'en', locales: ['en'] },

  presets: [
    [
      'classic',
      ({
        docs: {
          sidebarPath: './sidebars.js',
          // Optional: make "Edit this page" link point to your repo
          editUrl: 'https://github.com/brianronock/fullstackdev_spring-and-react/edit/main/fullstack-book/',
        },
        blog: false, // turn off blog for now
        theme: { customCss: './src/css/custom.css' },
      }),
    ],
  ],

  themeConfig: ({
    image: 'img/social-card.png',
    favicon: 'img/logo.svg',
    navbar: {
      title: 'Full Stack Book',
      logo: { alt: 'Book Logo', src: 'img/logo.svg' },
      items: [
        { type: 'docSidebar', sidebarId: 'tutorialSidebar', position: 'left', label: 'Table of Contents' },
        // { type: 'localeDropdown', position: 'right', },
        // { to: '/blog', label: 'Blog', position: 'left' },
        { href: 'https://github.com/brianronock/fullstackdev_spring-and-react', label: 'GitHub', position: 'right' },
      ],
    },
    footer: {
      style: 'dark',
      // links: [
      //   {
      //     title: 'Quick-Links', items: [
      //       { label: 'Introduction', to: '/docs/intro' },
      //       { label: 'Part 1', to: '/docs/part1/p1-ch1-intro' },
      //     ]
      //   },
      //   { title: 'Community', items: [{ label: 'Discord', href: 'https://discord.gg/...' }] },

      // ],
      copyright: `© ${new Date().getFullYear()} Brian Rono CK`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
      additionalLanguages: ['java', 'bash', 'json', 'yaml', 'sql', 'http', 'docker', 'properties', 'javascript', 'jsx'],
    },
  }),
};

export default config;
