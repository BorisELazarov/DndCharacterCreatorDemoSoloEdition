import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { FeatureService } from '../../../shared/services/feature-service/feature.service';
import { Feature } from '../../../shared/interfaces/feature';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-create-feature',
    standalone: true,
    imports: [MatFormFieldModule, ReactiveFormsModule, MatInputModule, CommonModule],
    templateUrl: './create-feature.component.html',
    styleUrl: './create-feature.component.css'
})
export class CreateFeatureComponent implements OnDestroy {
    private destroy = new Subject<void>();
    protected createForm: FormGroup;

    constructor(private service: FeatureService,
        fb: FormBuilder, private router: Router) {
        this.createForm = fb.group({
            name: ['', Validators.required],
            description: ['', Validators.required]
        });
    }


    submit() {
        let name = this.createForm.controls['name'].value;
        let description = this.createForm.controls['description'].value;
        let feature: Feature = {
            name: name,
            description: description
        };
        if (this.createForm.valid) {
            this.service.create(feature).pipe(
                takeUntil(this.destroy)
            ).subscribe();
            this.router.navigateByUrl('/features');
        }
        else {
            alert('Invalid input!');
        }
    }

    ngOnDestroy(): void {
        this.destroy.complete();
    }
}
