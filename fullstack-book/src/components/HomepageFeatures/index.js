import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';
import useBaseUrl from '@docusaurus/useBaseUrl';

const FeatureList = [
  {
    title: 'Backend (Spring Boot)',
    image: '/img/backend.svg',
    description: (
      <>
        Build a production‑ready REST API: layered architecture (controller → service → repository),
        DTO mapping with MapStruct, validation, global error handling, and security with CORS + JWT.
        Learn persistence with JPA/Hibernate, schema migrations via Flyway, caching with Redis, and
        observability using Spring Boot Actuator + Prometheus.
      </>
    ),
  },
  {
    title: 'Frontend (React)',
    image: '/img/frontend.svg',
    description: (
      <>
        Craft a modern SPA: component composition, forms and validation, API integration, and
        ergonomic UX (toasts, loading states, empty states). We start simple with hooks and Context,
        then contrast it with Redux Toolkit on data‑heavy screens—showing when each approach shines.
      </>
    ),
  },
  {
    title: 'DevOps & Infrastructure',
    image: '/img/devops.svg',
    description: (
      <>
        Ship with confidence: Dockerize services, wire CI/CD on GitHub Actions, and deploy to cloud.
        Add metrics, logs, and tracing; lock down secrets; enforce code quality with linting, tests,
        and PR checks. You’ll see how architecture choices ripple through deployment and operations.
      </>
    ),
  },
];

function Feature({image, title, description}) {
  const src = useBaseUrl(image);
  return (
    <div className={clsx('col col--4')}>
      <div className={styles.featureCard}>
        <div className={styles.featureMedia} role="img" aria-label={title}>
          <img src={src} alt={title} className={styles.featureImg} />
        </div>
        <div className="padding-horiz--md">
          <Heading as="h3" className={styles.featureTitle}>{title}</Heading>
          <p className={styles.featureText}>{description}</p>
        </div>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features} aria-labelledby="book-scope-heading">
      <div className="container">
        <Heading as="h2" id="book-scope-heading" className="text--center margin-bottom--lg">
          What You’ll Build & Learn
        </Heading>
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}