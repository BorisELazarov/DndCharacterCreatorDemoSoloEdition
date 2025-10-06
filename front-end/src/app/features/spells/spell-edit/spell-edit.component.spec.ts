import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpellEditComponent } from './spell-edit.component';

describe('SpellEditComponent', () => {
  let component: SpellEditComponent;
  let fixture: ComponentFixture<SpellEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpellEditComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpellEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
