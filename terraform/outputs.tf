output "ecr_repository_name" {
    description = "ECR repository name"
    value       = aws_ecr_repository.ms_franchise.name
}

output "ecr_repository_url" {
    description = "ECR repository URL"
    value       = aws_ecr_repository.ms_franchise.repository_url
}