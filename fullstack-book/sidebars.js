/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
  tutorialSidebar: [
    // front matter
    'preface',
    'intro',
    {
      type: 'category',
      label: 'Part I',
      items: [
        'part1/p1-ch1-intro',
        // add more chapters as you convert them:
        // 'part1/p1-ch2-domain-model',
        // 'part1/p1-ch3-dto-models',
      ],
    },
  ],
};

module.exports = sidebars;
