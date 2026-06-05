resource "aws_ecr_repository" "ms_franchise" {

    name                 = "ms-franchise"

    image_tag_mutability = "MUTABLE"

    image_scanning_configuration {
        scan_on_push = true
    }

    tags = {
        Project = "ms-franchise"
        Managed = "Terraform"
    }
}