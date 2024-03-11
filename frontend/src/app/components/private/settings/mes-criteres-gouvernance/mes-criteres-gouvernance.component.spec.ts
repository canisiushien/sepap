import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MesCriteresGouvernanceComponent } from './mes-criteres-gouvernance.component';

describe('MesCriteresGouvernanceComponent', () => {
  let component: MesCriteresGouvernanceComponent;
  let fixture: ComponentFixture<MesCriteresGouvernanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MesCriteresGouvernanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MesCriteresGouvernanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
