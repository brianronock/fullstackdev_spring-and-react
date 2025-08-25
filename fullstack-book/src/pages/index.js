import React from 'react';
import Layout from '@theme/Layout';
import Heading from '@theme/Heading';
import styles from './index.module.css';
import HomepageFeatures from '../components/HomepageFeatures';
import Link from '@docusaurus/Link';

export default function Home() {
  return (
    <Layout
      title="Full Stack Development with Spring Boot & React"
      description="A Comprehensive Hands-On Guide for Beginners and Intermediate Developers"
    >
       <header className={styles.hero}>
         <div className={styles.heroInner}>
           <h1 className={styles.title}>Full Stack Development with Spring Boot and React</h1>
           <p className={styles.subtitle}>
             A Comprehensive Hands‑On Guide for Beginners and Intermediate Developers
           </p>
           <div className={styles.ctaRow}>
             <Link className={styles.primaryBtn} to="/docs/preface">Read the Book for free!</Link>
           </div>
         </div>
       </header>

      <main className="container margin-vert--lg">
        <HomepageFeatures />
      </main>
    </Layout>
  );
}