# Prometheus Metrics Jira Attachments

These files accompany the Jira issue **Enable Prometheus and operation-level SOAP metrics for PCSS services**.

## Files

- `SoapMetricsInterceptor.java`: Complete example of the Spring-WS interceptor that records operation, caller application, outcome, and duration.
- `SoapMetricsConfiguration.java`: Registers the interceptor with Spring-WS.
- `application-observability.yml`: Spring Boot Actuator, Prometheus, management-port, common-tag, and histogram configuration.
- `deploymentconfig-observability-snippet.yaml`: OpenShift pod annotations, metrics port, and health-probe changes.
- `sysdig-promql-queries.promql`: Suggested Sysdig dashboard and alert queries.

## Adaptation required

The examples use package `ca.bc.gov.open.pcss.observability`. Criminal may use its existing `ca.bc.gov.open.pcsscriminalapplication` package structure instead. Copy the Java files into each application module and adjust the package declaration as needed.

Set the service-specific application name in `application-observability.yml`:

- `jag-pcss-common`
- `jag-pcss-civil`
- `jag-pcss-criminal`

The OpenShift file is a merge example, not a complete `DeploymentConfig`. Apply its fields to the existing template without replacing existing environment variables, resources, or deployment settings. Do not expose the management port through the public Service or Route.

The caller label is deliberately restricted to `PCSS`, `CCSS`, `OTHER`, and `UNSPECIFIED`. Do not replace this normalization with the unvalidated incoming value.
