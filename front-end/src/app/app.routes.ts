import { Routes } from '@angular/router';
import { ProficiencyListComponent } from './features/proficiencies/proficiency-list/proficiency-list.component';
import { ProficiencyDetailsComponent } from './features/proficiencies/proficiency-details/proficiency-details.component';
import { CreateProficiencyComponent } from './features/proficiencies/create-proficiency/create-proficiency.component';
import { EditProficiencyComponent } from './features/proficiencies/edit-proficiency/edit-proficiency.component';
import { ProficiencyDeletedListComponent } from './features/proficiencies/proficiency-deleted-list/proficiency-deleted-list.component';
import { ClassListComponent } from './features/classes/class-list/class-list.component';

export const routes: Routes = [
    {
        path:'proficiencies',
        component:ProficiencyListComponent,
        title:'Proficiency list'
    },
    {
        path:'proficiencies/deleted',
        component:ProficiencyDeletedListComponent,
        title:'Deleted proficiency list'
    },
    {
        path:'proficiencies/create',
        component:CreateProficiencyComponent,
        title:'Create proficiency'
    },
    {
        path:'proficiencies/edit/:id',
        component:EditProficiencyComponent,
        title:'Edit proficiency'
    },
    {
        path:'proficiencies/:id',
        component:ProficiencyDetailsComponent,
        title:'Proficiency details'
    },
    {
        path:'classes',
        component:ClassListComponent,
        title:'Class list'
    }
];
