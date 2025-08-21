import React from 'react';
import Link from '@docusaurus/Link';
import clsx from 'clsx';
import {useAllDocsData} from '@docusaurus/plugin-content-docs/client';
import styles from './styles.module.css';

function useDescription(permalink) {
  const allDocs = useAllDocsData(); // { [pluginId]: {versions: [{docs: [...] }]} }
  for (const pluginId of Object.keys(allDocs)) {
    for (const v of allDocs[pluginId].versions) {
      const hit = v.docs.find((d) => d.permalink === permalink);
      if (hit) return hit.description || hit.frontMatter?.description || '';
    }
  }
  return '';
}

export default function DocPaginator({previous, next}) {
  const prevDesc = previous ? useDescription(previous.permalink) : '';
  const nextDesc = next ? useDescription(next.permalink) : '';

  return (
    <nav className="pagination-nav" aria-label="Docs pages navigation">
      {previous && (
        <Link className={clsx('pagination-nav__link', 'pagination-nav__link--prev')} to={previous.permalink}>
          <div className="pagination-nav__sublabel">Previous</div>
          <div className="pagination-nav__label">{previous.title}</div>
          {prevDesc && <div className={styles.desc}>{prevDesc}</div>}
        </Link>
      )}
      {next && (
        <Link className={clsx('pagination-nav__link', 'pagination-nav__link--next')} to={next.permalink}>
          <div className="pagination-nav__sublabel">Next</div>
          <div className="pagination-nav__label">{next.title}</div>
          {nextDesc && <div className={styles.desc}>{nextDesc}</div>}
        </Link>
      )}
    </nav>
  );
}