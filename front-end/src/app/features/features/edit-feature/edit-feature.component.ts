import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Feature } from '../../../shared/interfaces/feature';
import { FeatureService } from '../../../shared/services/feature-service/feature.service';

@Component({
    selector: 'app-edit-feature',
    standalone: true,
    imports: [MatFormFieldModule, CommonModule, ReactiveFormsModule],
    templateUrl: './edit-feature.component.html',
    styleUrl: './edit-feature.component.css'
})
export class EditFeatureComponent implements OnInit, OnDestroy {
    private destroy = new Subject<void>();
    protected editForm: FormGroup;

    constructor(private service: FeatureService,
        fb: FormBuilder, private router: Router, route: ActivatedRoute) {
        this.editForm = fb.group({
            id: [Number(route.snapshot.params['id'])],
            name: ['', Validators.required],
            description: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        this.service.getById(this.editForm.controls['id'].value)
            .pipe(takeUntil(this.destroy))
            .subscribe(
                response => {
                    this.editForm.controls['name'].setValue(response.body?.name);
                    this.editForm.controls['description'].setValue(response.body?.description);
                }
            );
    }
    

    submit() {
        let id = this.editForm.controls['id'].value;
        let name = this.editForm.controls['name'].value;
        let description = this.editForm.controls['description'].value;
        let feature: Feature = {
            id: id,
            name: name,
            description: description
        };
        if (this.editForm.valid) {
            this.service.edit(feature).pipe(
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
