import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpellCreateComponent } from './spell-create.component';

describe('SpellCreateComponent', () => {
  let component: SpellCreateComponent;
  let fixture: ComponentFixture<SpellCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpellCreateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpellCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
