resource "aws_cloudwatch_metric_alarm" "high_error_rate_alarm" {
  alarm_name          = "pricing-service-major-incident"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 1
  metric_name = "5XXErrors"
  period              = 60 # 1 minuto (segundos)
  statistic           = "Sum"
  threshold           = 100 # Num. de KOs / 500s
  alarm_description = "Se han detectado +100 errores 5xx en 1 minuto. Posible caída del servicio."
}
