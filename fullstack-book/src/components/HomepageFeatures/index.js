import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

const FeatureList = [
    {
        title: 'Backend (Spring Boot)',
        Svg: require('@site/static/img/backend.svg').default,
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
        Svg: require('@site/static/img/frontend.svg').default,
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
        Svg: require('@site/static/img/devops.svg').default,
        description: (
            <>
                Ship with confidence: Dockerize services, wire CI/CD on GitHub Actions, and deploy to cloud.
                Add metrics, logs, and tracing; lock down secrets; enforce code quality with linting, tests,
                and PR checks. You’ll see how architecture choices ripple through deployment and operations.
            </>
        ),
    },
];

function Feature({Svg, title, description}) {
    return (
        <div className={clsx('col col--4')}>
            <div className="text--center" role="img" aria-label={title}>
                <Svg className={styles.featureSvg} />
            </div>
            <div className="text--center padding-horiz--md">
                <Heading as="h3">{title}</Heading>
                <p>{description}</p>
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
